#version 120

varying vec2 v_texCoord0;

void main() {
    gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
}
