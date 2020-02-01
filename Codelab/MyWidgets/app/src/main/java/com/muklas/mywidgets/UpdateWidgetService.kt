package com.muklas.mywidgets

import android.app.job.JobParameters
import android.app.job.JobService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.widget.RemoteViews

class UpdateWidgetService: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        val manager = AppWidgetManager.getInstance(this)
        val view = RemoteViews(packageName, R.layout.random_numbers_widget)
        val theWidget = ComponentName(this,RandomNumberWidget::class.java)
        val lastUpdate = "Random: "+ NumberGenerator.generate(100)
        view.setTextViewText(R.id.appwidget_text, lastUpdate)
        manager.updateAppWidget(theWidget, view)
        jobFinished(params, false)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}