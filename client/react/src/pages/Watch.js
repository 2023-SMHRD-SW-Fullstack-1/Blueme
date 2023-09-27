/*
작성자: 신지훈
날짜: 2023-09-08
설명: Three.js 상세 설정
*/

import React, { useRef, useEffect } from "react";
import { useGLTF } from "@react-three/drei";
import { useFrame } from "@react-three/fiber";
import * as THREE from "three";

export function Watch(props) {
  const groupRef = useRef();
  const { nodes, materials } = useGLTF("/watch/scene.gltf");

  useEffect(() => {
    if (groupRef.current) {
      const bbox = new THREE.Box3().setFromObject(groupRef.current);
      const center = bbox.getCenter(new THREE.Vector3());

      groupRef.current.position.x += -center.x;
      groupRef.current.position.y += -center.y;
      groupRef.current.position.z += -center.z;
    }
  }, []);

  useFrame(() => {
    if (groupRef.current) {
      groupRef.current.rotation.y += 0.05;
    }
  });

  return (
    <group ref={groupRef} {...props} dispose={null}>
      <mesh geometry={nodes.pasted__polySurface30_pasted__blinn9_0.geometry} material={materials.pasted__blinn9} />
      <mesh geometry={nodes.pasted__polySurface31_pasted__blinn25_0.geometry} material={materials.pasted__blinn25} />
      <mesh geometry={nodes.pasted__polySurface11_pasted__blinn5_0.geometry} material={materials.pasted__blinn5} />
      <mesh geometry={nodes.pasted__polySurface2_pasted__blinn1_0.geometry} material={materials.pasted__blinn1} />
      <mesh geometry={nodes.pasted__polySurface9_pasted__blinn2_0.geometry} material={materials.pasted__blinn2} />
      <mesh geometry={nodes.pasted__polySurface12_pasted__blinn4_0.geometry} material={materials.pasted__blinn4} />
      <mesh geometry={nodes.pasted__polySurface18_pasted__blinn11_0.geometry} material={materials.pasted__blinn11} />
      <mesh geometry={nodes.pasted__polySurface18_pasted__blinn26_0.geometry} material={materials.pasted__blinn26} />
      <mesh geometry={nodes.pasted__polySurface24_pasted__blinn24_0.geometry} material={materials.pasted__blinn24} />
      <mesh geometry={nodes.pasted__polySurface24_pasted__blinn23_0.geometry} material={materials.pasted__blinn23} />
      <mesh geometry={nodes.pasted__polySurface29_pasted__blinn22_0.geometry} material={materials.pasted__blinn22} />
    </group>
  );
}

useGLTF.preload("/watch/scene.gltf");
