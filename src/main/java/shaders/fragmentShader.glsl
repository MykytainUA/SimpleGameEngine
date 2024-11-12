#version 430 

// Defines are added here

// %ADD_DEFINES% - Macro placeholder

// End adding defines

uniform mat4 p_matrix;
uniform mat4 v_matrix;

#ifdef SOLID_COLOR_COMPONENT
	in vec4 posColor;
#endif

#ifdef TEXTURE_COMPONENT
	layout (binding=0) uniform sampler2D samp;
	in vec2 texCoord;
#endif

out vec4 color;

void main(void)
{ 
	#ifdef SOLID_COLOR_COMPONENT
		color = posColor;
	#endif

	#ifdef TEXTURE_COMPONENT
		color = texture2D(samp, texCoord);
	#endif
} 