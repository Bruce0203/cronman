package io.github.bruce0203.cronman

import it.sauronsoftware.cron4j.Scheduler;
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin() {

    val schedulers = ArrayList<Scheduler>()

    override fun onEnable() {
        loadDefaultConfig()
        config.getKeys(false).forEach { key ->
            val cmd = config.getString(key)!!
            registerScheduler(key) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd)
            }
        }
    }

    private fun loadDefaultConfig() {
        config.options().copyDefaults()
        saveDefaultConfig()
    }

    private fun registerScheduler(cronString: String, exec: Runnable) {
        Scheduler().apply {
            schedule(cronString, exec)
            start()
        }
    }

    override fun onDisable() {
        schedulers.forEach(Scheduler::stop)
    }

}