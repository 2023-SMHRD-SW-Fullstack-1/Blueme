/*
작성자: 신지훈
날짜: 2023-09-08
설명: Three.js 적용
*/
/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: ChatGPT 음악 추천 로딩 화면 
*/

import React from "react";
import { Canvas, useThree, useFrame } from "@react-three/fiber";
import { Model } from "../Model";
import { OrbitControls } from "@react-three/drei";
import { useNavigate } from "react-router";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import axios from 'axios'

//Three.js
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

  const user = useSelector(state => state.memberReducer.user)
  const nickname = user.nickname
  console.log('header',user);
  const id = user.id //id가져오기


  //6초 로딩 
  const timeout = () => {
    setTimeout(() => {
        axios
        .post(`http://172.30.1.27:8104/recMusiclist/chatGPT/${id}`)//ChatGPT 추천 받디
        .then((res) => {
          console.log(res)
          navigate("/RecPlayList");
        })
        .catch((err) => console.log(err))
    }, 1000);
  };

  useEffect(() => {
    timeout();
    return () => {
      clearTimeout(timeout);
    };
  });

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 p-10 text-center text-custom-white flex flex-col h-full justify-center items-center text-xl tracking-tight space-y-10">
      <p className="">{nickname}님의 건강데이터를 기반으로 <br/> GPT가 추천해주고 있어요</p>

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
          <OrbitControls minDistance={2} maxDistance={10} />
        </Canvas>
      </div>
    </div>
  );
};

export default LoadGpt;
