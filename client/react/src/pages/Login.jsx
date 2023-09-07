import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import logo2 from "../assets/img/logo2.png";
import kakao from "../assets/img/kakao.png";
import google from "../assets/img/google.png";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // 로그인 버튼 클릭 시 실행되는 함수
  const handleLogin = async () => {
    try {
      const response = await axios.post("/user/login", { email, password });

      console.log(response);

      if (response.status === 200) {
        alert("로그인에 성공했습니다!");
        // 여기서 필요한 경우 로그인 후의 동작을 수행하실 수 있습니다.
        // 예: 페이지 이동 등
      }
    } catch (error) {
      console.error(error);
      alert("로그인에 실패했습니다.");
    }
  };

  return (
    <div className=" min-h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 flex flex-col px-4 sm:px-8 md:px-16">
      <div className="mt-36 text-custom-white mb-3 text-center sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
        <Link
          to="/Main"
          className="self-center flex items-center justify-center"
        >
          <span className="text-3xl tracking-tighter font-bold text-[rgba(255,255,255,0.80)]">Blueme</span>
          <img src={logo2} className="w-full max-w-[60px] h-auto" alt="Blueme 로고" />
        </Link>
      </div>

      <input
        type="email"
        onChange={(e) => setEmail(e.target.value)}
        className="bg-gradient-to-t from-gray-900 tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-12 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
        placeholder="이메일을 입력해주세요."
      />

      <input
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        className="bg-gradient-to-t from-gray-900 tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 text-custom-white w-full mt-3 rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
        placeholder="비밀번호를 입력해주세요."
      />

      <button
        onClick={handleLogin}
        className="
             mt-10
             h-11
             px-3 h-10 relative 
             bg-[#221a38]  
             rounded-lg border border-soild border-[#fdfdfd]
             text-custom-white
             tracking-tighter
             font-bold"
             
      >
        로그인
      </button>

      <hr
        className="mt-10"
        style={{ borderTop: "1px solid gray", width: "100%" }}
      />
      <div className="text-custom-white mt-10 mb-4 text-left w-full text-2xl font-semibold tracking-tighter">
        SNS 로그인
      </div>

      <button
        className="
     flex items-center justify-center pl-2 w-full mt-6 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
      >
        <img src={kakao} alt="Kakao logo" className="w-[6%] h-auto mr-1 tracking-tighter" />
        카카오로 로그인
      </button>

      <button
        className="
  flex items-center justify-center pl-2 w-full mt-3 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral[200 "
      >
        <img src={google} alt="Google logo" className="mr-1 w-[5%] h-auto tracking-tighter" />
        구글로 로그인
      </button>

      <hr
        className="mt-10"
        style={{ borderTop: "1px solid gray", width: "100%" }}
      />

      <div className="text-custom-white mt-6 text-xl font-semibold tracking-tighter text-center">
        <Link to="/Join">
          <button >
            회원가입
          </button>
        </Link>
      </div>
    </div>
  );
};

export default Login;
