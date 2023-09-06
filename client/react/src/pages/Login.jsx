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
    <div className=" min-h-screen bg-custom-blue flex flex-col items-center justify-center px-4 sm:px-8 md:px-16">
      <div className="mt-5 text-custom-white mb-3 text-center sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
        <Link to="/Main" className="self-center flex items-center justify-center">
          <span className="text-xl text-[rgba(255,255,255,0.80)]">Blueme</span>
          <img src={logo2} className="w-full max-w-[100px] h-auto" alt="" />
        </Link>
      </div>

      <input
        type="email"
        onChange={(e) => setEmail(e.target.value)}
        className="focus:border-custom-white pl-2 w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
        placeholder="이메일을 입력해주세요."
      />

      <input
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        className="focus:border-custom-white pl-2 text-custom-white w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
        placeholder="비밀번호를 입력해주세요."
      />

      <button
        onClick={handleLogin}
        className="
             mt-5
             w-full
             px-3 h-10 relative 
             bg-[#221a38]  
             rounded-lg border border-soild border-[#fdfdfd]
             text-custom-white"
      >
        로그인
      </button>

      <hr className="mt-5" style={{ borderTop: "1px solid gray", width: "100%" }} />
      <div className="text-custom-white mt-5 mb-4 text-lfet w-full ">SNS 로그인</div>

      <button
        className="
     flex items-center justify-center pl-2 w-full mt-2 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
      >
        <img src={kakao} alt="" className="w-[6%] h-auto mr-1" />
        카카오로 로그인
      </button>

      <button
        className="
  flex items-center justify-center pl-2 w-full mt-3 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral[200 "
      >
        <img src={google} alt="Google logo" className="mr-1 w-[5%] h-auto" />
        구글로 로그인
      </button>

      <hr className="mt-5" style={{ borderTop: "1px solid gray", width: "100%" }} />

      <div>
        <Link to="/Join">
          <button className="text-custom-white mt-5 mb-5 text-xl">회원가입</button>
        </Link>
      </div>
    </div>
  );
};

export default Login;
