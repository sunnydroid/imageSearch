package com.sunny.app.googleimagesearch;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;


/**
 * Endless scroll listener
 * @author sunshah
 *
 */
public abstract class EndlessScrollListner implements OnScrollListener {

	private int visibleThreshold = 5;
	private int currentPage = 0;
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private int startPageindex = 0;
	
	public EndlessScrollListner() {
		
	}
	
	public EndlessScrollListner(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}
	
	public EndlessScrollListner(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.currentPage = startPage;
		this.startPageindex = startPage;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		
		if(totalItemCount < previousTotalItemCount) {
			/* List is invalidated, reset to initial state */
			this.currentPage = this.startPageindex;
			this.previousTotalItemCount = totalItemCount;
			if(totalItemCount == 0) {
				loading = true;
			}
		}
		
		/* If loading, check if dataset count has updated, if it has,
		 * loading has finished
		 */
		if(loading && (totalItemCount > previousTotalItemCount)) {
			loading = false;
			previousTotalItemCount = totalItemCount;
			currentPage++;
		}
		
		/* If not loading, check if we have breached visible threshold 
		 * and need to load more data. If we do, execute onLoadMore()
		 */
		if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			Toast.makeText(view.getContext(), "Loading", Toast.LENGTH_SHORT).show();
			onLoadMore(currentPage + 1, totalItemCount);
			loading = true;
		}
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	public abstract void onLoadMore(int page, int totalItemsCount);

}
