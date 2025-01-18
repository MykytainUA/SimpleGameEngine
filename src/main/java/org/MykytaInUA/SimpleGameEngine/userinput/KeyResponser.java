package org.mykytainua.simplegameengine.userinput;

import java.util.Set;

/**
 * An interface representing an entity that responds to key press events.
 */
public interface KeyResponser {

    /**
     * Applies the current set of pressed keys to the implementing object.
     *
     * @param pressedKeys a {@code Set} of integers representing the keys that are
     *                    currently pressed. Each integer corresponds to a key code.
     */
    public void applyPressedKeys(Set<Integer> pressedKeys);
}
