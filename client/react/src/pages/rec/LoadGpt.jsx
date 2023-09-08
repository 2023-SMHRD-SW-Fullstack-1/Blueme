import React, { useEffect, useState } from "react";
import loading from "../../assets/img/loading.png";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import { Canvas } from "@react-three/fiber";
import { Model } from "../Model";
import { OrbitControls } from "@react-three/drei";

const LoadGpt = () => {
  const navigate = useNavigate();

  //3초 후 RecPlayList로 이동
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
    // GPT 추천 로딩 화면
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white flex flex-col min-h-screen justify-center items-center text-2xl font-semibold tracking-tighter">
      <p className="mb-3">GPT가 추천해주고 있어요</p>
      <img
        src={loading}
        alt="내 데이터 로딩 중"
        class="object-cover w-[180px] h-[20px] mb-28"
      />
    </div>
  );
};

export default LoadGpt;
