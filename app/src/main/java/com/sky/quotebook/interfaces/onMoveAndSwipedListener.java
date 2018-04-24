package com.sky.quotebook.interfaces;

/**
 * Created by Yasir on 3/19/2018.
 */

public interface onMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
