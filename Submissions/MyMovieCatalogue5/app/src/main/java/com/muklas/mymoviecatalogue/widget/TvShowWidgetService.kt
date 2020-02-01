package com.muklas.mymoviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

class TvShowWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        TvShowRemoteViewsFactory(this.applicationContext)
}