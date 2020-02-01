package com.muklas.mymoviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

class MovieWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        MovieRemoteViewsFactory(this.applicationContext)
}