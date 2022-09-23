package io.github.bruce0203.cronman

import com.cronutils.model.CronType.QUARTZ
import com.cronutils.descriptor.CronDescriptor
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.parser.CronParser
import it.sauronsoftware.cron4j.Scheduler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@Suppress("unused")
class Plugin : JavaPlugin() {

    val schedulers = ArrayList<Scheduler>()

    override fun onEnable() {
        loadDefaultConfig()
        config.getKeys(false).forEach { key -> registerScheduler(key, config.getString(key)!!) }
    }

    private fun loadDefaultConfig() {
        config.options().copyDefaults()
        saveDefaultConfig()
    }

    private fun registerScheduler(cronString: String, cmd: String) {
        Scheduler().apply {
            schedule(cronString) {
                Bukkit.getScheduler().runTask(this@Plugin , Runnable {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd)
                })
            }
            val description: String = CronDescriptor.instance(Locale.KOREAN)
                .describe(
                    CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ))
                        .parse(cronString))
            logger.info("[$description]: $cmd")
            start()
            schedulers.add(this)
        }
    }

    override fun onDisable() {
        schedulers.forEach(Scheduler::stop)
    }

}