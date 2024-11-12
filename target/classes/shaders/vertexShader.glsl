#version 430

// Defines are added here
// %ADD_DEFINES% - Macro placeholder

// End adding defines

// Layouts depending on defines
layout (location = 0) in vec3 vertices;

#ifdef POSITION_COMPONENT
	layout (location = 1) in vec3 instancePosition;
#endif

#ifdef ROTATION_COMPONENT
	layout (location = 2) in vec3 instanceRotation;
#endif

#ifdef SIZE_COMPONENT
	layout (location = 3) in vec3 instanceSize;
#endif

#ifdef SOLID_COLOR_COMPONENT
	layout (location = 4) in vec4 instanceColor;
#endif

#ifdef TEXTURE_COMPONENT
	layout (location = 4) in vec2 textureCoordinate;
	layout (binding = 0) uniform sampler2D samp;
#endif
//

// Uniforms
uniform mat4 p_matrix;
uniform mat4 v_matrix;
//

// Output to fragment shader
#ifdef SOLID_COLOR_COMPONENT
	out vec4 posColor;
#endif

#ifdef TEXTURE_COMPONENT
	out vec2 texCoord;
#endif
//

mat4 buildTranslate(float x, float y, float z);
mat4 buildRotateX(float rad);
mat4 buildRotateY(float rad);
mat4 buildRotateZ(float rad);
mat4 buildScale(float x, float y, float z);

void main(void) 
{
	vec4 position = vec4(vertices, 1.0);

	#ifdef SIZE_COMPONENT
		position = buildScale(instanceSize.x, instanceSize.y, instanceSize.z) * position;
	#endif

	#ifdef ROTATION_COMPONENT
		position = buildRotateZ(instanceRotation.z) * position;
		position = buildRotateY(instanceRotation.y) * position;
		position = buildRotateX(instanceRotation.x) * position;
	#endif

	#ifdef POSITION_COMPONENT
		position = buildTranslate(instancePosition.x, instancePosition.y, instancePosition.z) * position;
	#endif

	position = v_matrix * position;
	position = p_matrix * position;

	gl_Position = position;

	#ifdef SOLID_COLOR_COMPONENT
		posColor = instanceColor;
	#endif

	#ifdef TEXTURE_COMPONENT
		texCoord = textureCoordinate;
	#endif
}

mat4 buildTranslate(float x, float y, float z)
{
	return mat4(1.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 1.0, 0.0,
				x, y, z, 1.0);
}

mat4 buildRotateX(float rad)
{
	return mat4(1.0, 0.0, 0.0, 0.0,
				0.0, cos(rad), -sin(rad), 0.0,
				0.0, sin(rad), cos(rad), 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 buildRotateY(float rad)
{
	return mat4(cos(rad), 0.0, sin(rad), 0.0,
				0.0, 1.0, 0.0, 0.0,
				-sin(rad), 0.0, cos(rad), 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 buildRotateZ(float rad)
{
	return mat4(cos(rad), -sin(rad), 0.0, 0.0,
				sin(rad), cos(rad), 0.0, 0.0,
				0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 0.0, 1.0);
}

mat4 buildScale(float x, float y, float z)
{
	return mat4(x, 0.0, 0.0, 0.0,
				0.0, y, 0.0, 0.0,
				0.0, 0.0, z, 0.0,
				0.0, 0.0, 0.0, 1.0);
}