package com.samirk433.quotebook.interfaces;

public interface onMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
