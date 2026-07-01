package com.hybris.plaincontainers

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.hybris.plaincontainers.data.AppDatabaseManager
import com.hybris.plaincontainers.data.JsonManager
import com.hybris.plaincontainers.data.LocaleUtils
import com.hybris.plaincontainers.data.SettingsManager
import com.hybris.plaincontainers.data.viewmodels.AppBarViewModel

/**
 * App entry point.
 * Sets up any managers, the AppBar + its ViewModel, NavGraph and Locale.
 * Also sets a new context when the locale changes.
 */
class MainActivity : AppCompatActivity() {
    private val appbarVm: AppBarViewModel by viewModels()
    private lateinit var layoutToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutToolbar = findViewById(R.id.layoutToolbar)
        layoutToolbar.title = "Container Overview"
        layoutToolbar.subtitle = ""
        setSupportActionBar(layoutToolbar)

        JsonManager.init(this)
        AppDatabaseManager.init(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        layoutToolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        val appbarConfiguration = AppBarConfiguration(
            setOf(R.id.containerOverviewFragment)
        )
        NavigationUI.setupWithNavController(layoutToolbar, navController, appbarConfiguration)

        navController.setGraph(R.navigation.nav_graph)

        appbarVm.model.observe(this) { model ->
            layoutToolbar.apply {
                subtitle = model.subtitle
            }
        }

        initSettings()
    }

    private fun initSettings() {
        val settings = SettingsManager.getSettings()

        val currentLang = LocaleUtils.getLocale(this).toLanguageTag()
        val storedLang = settings.locale.toLanguageTag()

        // Indirectly call this.attachBaseContext with correct locale.
        if(currentLang != storedLang) {
            val locale = LocaleUtils.getLocaleFromLanguageTag(storedLang)
            LocaleUtils.setLocale(this, locale)
            recreate()
        }
    }


    /**
     * Gets the currently set locale from LocaleUtils and attaches a new context with that locale.
     * This function is normally called on Activity recreation,
     * specifically when SettingsFragment calls recreate() on language change confirm.
     * ```
     * From class Android.Content.ContextWrapper:
     * Set the base context for this ContextWrapper.
     * All calls will then be delegated to the base context.
     * ```
     * @throws IllegalStateException if a base context has already been set.
     * @param base The new base context for this wrapper.
     */
    override fun attachBaseContext(base: Context) {
        val langTag = LocaleUtils.getLocale(base).toLanguageTag()
        val locale = LocaleUtils.getLocaleFromLanguageTag(langTag)
        val newBase = LocaleUtils.setLocale(base, locale)

        super.attachBaseContext(newBase)
    }
}
