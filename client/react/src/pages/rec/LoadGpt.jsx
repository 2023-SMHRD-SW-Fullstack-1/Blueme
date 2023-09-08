import React, { useEffect, useState } from "react";
import loading from "../../assets/img/loading.png";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import { Canvas } from "@react-three/fiber";
import { Model } from "../Model";
import { OrbitControls } from "@react-three/drei";

const LoadGpt = () => {
  const navigate = useNavigate();
  const color = "white"; // 색상을 white로 설정
  const intensity = 1; // 강도를 1로 설정
  const timeout = () => {
    setTimeout(() => {
      navigate("/RecPlayList");
    }, 5000);
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

      <div className=" from-gray-900 via-stone-950 to-gray-700 mb-10 ">
        <Canvas
          camera={{
            fov: 30,
            near: 1,
            aspect: window.innerWidth / window.innerHeight,
            far: 100,
            position: [0, 0, 1],
          }}
        >
          <pointLight color={color} intensity={10} />
          <ambientLight intensity={10} />
          <directionalLight
            castShadow
            position={[0, 20, 0]}
            intensity={10}
            shadow-mapSize-width={1024}
            shadow-mapSize-height={1024}
            shadow-camera-far={50}
            shadow-camera-left={-10}
            shadow-camera-right={10}
            shadow-camera-top={10}
            shadow-camera-bottom={-10}
          />
          <Model />
          <OrbitControls />
        </Canvas>
      </div>
    </div>
  );
};

export default LoadGpt;
