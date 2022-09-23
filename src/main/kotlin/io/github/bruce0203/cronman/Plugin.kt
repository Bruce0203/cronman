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
        config.getKeys(false).forEach { key ->
            val cmd = config.getString(key)!!
            registerScheduler(key, cmd)
        }
    }

    private fun loadDefaultConfig() {
        config.options().copyDefaults()
        saveDefaultConfig()
    }

    private fun registerScheduler(cronString: String, cmd: String) {
        Scheduler().apply {
            schedule(cronString) { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd) }
            val descriptor: CronDescriptor = CronDescriptor.instance(Locale.UK)
            val parser = CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ))
            val description: String = descriptor.describe(parser.parse(cronString))
            logger.info("[$description]: $cmd")
            start()
            schedulers.add(this)
        }
    }

    override fun onDisable() {
        schedulers.forEach(Scheduler::stop)
    }

}