/*
작성자: 신지훈
날짜: 2023-09-08
설명: Three.js 적용
*/

import React from "react";
import { Canvas, useThree, useFrame } from "@react-three/fiber";
import { Model } from "../Model";
import { OrbitControls } from "@react-three/drei";
import { useNavigate } from "react-router";
import { useEffect } from "react";

/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: ChatGPT 음악 추천 로딩 화면 
*/

const DirectionalLightWithCamera = () => {
  const { camera } = useThree();
  const lightRef = React.useRef();

  useFrame(() => {
    if (lightRef.current) {
      lightRef.current.position.copy(camera.position);
    }
  });

  return (
    <directionalLight
      ref={lightRef}
      castShadow
      intensity={50}
      shadow-mapSize-width={1024}
      shadow-mapSize-height={1024}
      shadow-camera-far={50}
      shadow-camera-left={-10}
      shadow-camera-right={10}
      shadow-camera-top={10}
      shadow-camera-bottom={-10}
    />
  );
};

const LoadGpt = () => {
  const navigate = useNavigate();

  const timeout = () => {
    setTimeout(() => {
      navigate("/RecPlayList");
    }, 3000);
  };

  useEffect(() => {
    timeout();
    return () => {
      clearTimeout(timeout);
    };
  });

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white flex flex-col h-full justify-center items-center text-2xl font-semibold tracking-tighter space-y-10">
      <p className="mb-3">GPT가 추천해주고 있어요</p>

      <div className="from-gray-900 via-stone-950 to-gray-700 w-[100%]">
        <Canvas
          camera={{
            fov: 30,
            near: 1,
            aspect: window.innerWidth / window.innerHeight,
            far: 100,
            position: [0, 0, 1.4],
          }}
        >
          <pointLight color={"white"} intensity={50} />
          <ambientLight intensity={50} />
          <DirectionalLightWithCamera />
          <Model />
          <OrbitControls minDistance={1} maxDistance={10} />
        </Canvas>
      </div>
    </div>
  );
};

export default LoadGpt;
