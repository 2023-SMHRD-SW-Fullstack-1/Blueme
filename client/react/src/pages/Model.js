/*
작성자: 신지훈
날짜: 2023-09-08
설명: Three.js 상세 설정
*/

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

      gltf.scene.position.x = -center.x;
      gltf.scene.position.y = -center.y;

      // Change the color of all meshes to green
      gltf.scene.traverse((object) => {
        if (object.isMesh) {
          object.material.color.setRGB(16, 163, 127);
        }
      });
    }
  }, [gltf]);
  useFrame(() => {
    if (groupRef.current) {
      groupRef.current.rotation.y += 0.03;
    }
  });

  return <primitive object={gltf.scene} ref={groupRef} position={[0, 0, 0]} />;
}

useGLTF.preload("/scene.gltf");
