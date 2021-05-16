package com.app.trackit.ui.components;

import com.app.trackit.ui.animation.FabAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpandableFab {

    private boolean clicked;
    protected FloatingActionButton mainFab;
    protected FloatingActionButton secondaryFab;
    protected FloatingActionButton tertiaryFab;

    public ExpandableFab(FloatingActionButton mainFab,
                         FloatingActionButton secondaryFab,
                         FloatingActionButton tertiaryFab) {
        this.mainFab = mainFab;
        this.secondaryFab = secondaryFab;
        this.tertiaryFab = tertiaryFab;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public FloatingActionButton getMainFab() {
        return mainFab;
    }

    public FloatingActionButton getSecondaryFab() {
        return secondaryFab;
    }

    public FloatingActionButton getTertiaryFab() {
        return tertiaryFab;
    }

    public void onClick() {
        clicked = FabAnimation.rotateFab(this.mainFab, !clicked);
        if (clicked) {
            FabAnimation.showIn(this.secondaryFab);
            FabAnimation.showIn(this.tertiaryFab);
        } else {
            FabAnimation.showOut(this.secondaryFab);
            FabAnimation.showOut(this.tertiaryFab);
        }
    }

}
