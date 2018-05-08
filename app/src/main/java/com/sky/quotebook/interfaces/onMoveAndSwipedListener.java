package com.sky.quotebook.interfaces;

public interface onMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
