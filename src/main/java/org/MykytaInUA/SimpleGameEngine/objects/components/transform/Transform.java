package org.mykytainua.simplegameengine.objects.components.transform;

import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * Marker interface for components that represent transformations in 3D space.
 * 
 * <p>Classes implementing this interface are expected to provide 
 * transformation-related functionality, such as position, rotation, or scale,
 * and adhere to the {@link Component} interface requirements.</p>
 * 
 * <p>This interface does not define additional methods beyond those specified 
 * in {@link Component}, but serves to categorize transformation-related 
 * components.</p>
 */
public interface Transform extends Component {

}
