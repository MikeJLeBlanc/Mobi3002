package com.example.m05_fragments_v03;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.util.Log;
import android.view.MenuItem;

import com.example.m05_fragments_v03.dummy.DummyContent;


/**
 * An activity representing a single NewsArticle detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NewsArticleListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link NewsArticleDetailFragment}.
 */
public class NewsArticleDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsarticle_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(NewsArticleDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(NewsArticleDetailFragment.ARG_ITEM_ID));
            NewsArticleDetailFragment fragment = new NewsArticleDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.newsarticle_detail_container, fragment)
                    .commit();

            // Get title for this article
            String art_title = DummyContent.ITEM_MAP.get(arguments.getString(NewsArticleDetailFragment.ARG_ITEM_ID)).getTitle();

            // Set Activity Label to this article name
            getActionBar().setTitle(art_title);
            Log.v("frags_V03", "Selected=" + art_title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, NewsArticleListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
