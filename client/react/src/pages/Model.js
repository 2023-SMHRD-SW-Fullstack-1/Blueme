import React, { useRef, useEffect } from "react";
import { useGLTF } from "@react-three/drei";
import { useFrame } from "@react-three/fiber";
import * as THREE from "three";

export function Model(props) {
  const groupRef = useRef();
  const gltf = useGLTF("/scene.gltf");

  // Calculate the bounding box of the model after it's loaded
  useEffect(() => {
    if (gltf.scene) {
      const bbox = new THREE.Box3().setFromObject(gltf.scene);
      const center = bbox.getCenter(new THREE.Vector3());

      // Use this center for offsetting the model
      gltf.scene.position.x += -0.5 * center.x;
      gltf.scene.position.y += -0.5 * center.y;
    }
  }, [gltf]);

  useFrame(() => {
    if (groupRef.current) {
      groupRef.current.rotation.y += 0.01;
    }
  });

  return <primitive object={gltf.scene} ref={groupRef} position={[0, 0, 0]} />;
}

useGLTF.preload("/public/scene.gltf");
