import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import logo2 from "../assets/img/logo2.png";
import kakao from "../assets/img/kakao.png";
import google from "../assets/img/google.png";

// import { useDispatch } from "react-redux";
// import { useSelector } from "react-redux";
// import { loginUser } from "../store/Users";
// import { SET_TOKEN } from "../store/Auth";

/*
작성자: 이유영
날짜(수정포함): 2023-09-11
설명: 로그인 구현
*/

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [id, setId] = useState("");
  const [isLoggendIn, setIsLoggendIn] = useState(false);
  const [token, setToken] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const requestData = {
      email: email,
      password: password,
    };

    await axios
      .post("http://172.30.1.45:8104/login", requestData)
      .then((res) => {
        let accessToken = res.headers.get("Authorization");
        let refreshToken = res.headers["authorization-refresh"];
        let email = res.data.email;
        let nickname = res.data.nickname;
        let id = res.data.id;
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("email", email);
        localStorage.setItem("nickname", nickname);
        localStorage.setItem("id", id);
        console.log(res);
        navigate("/");
      })
      .catch((e) => console.log(e));
  };

  return (
    <div className=" min-h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 flex flex-col px-4 sm:px-8 md:px-16">
      <div className="mt-36 text-custom-white mb-3 md:items-center justify-center text-center sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4"></div>

      <div className="flex items-start md:items-center justify-center h-full md:h-screen w-full  p-4">
        <div className="md:w-1/3 w-full">
          <Link to="/Main" className="self-center flex items-center justify-center">
            <span className="text-3xl tracking-tighter font-bold text-[rgba(255,255,255,0.80)]">Blueme</span>
            <img src={logo2} className="w-full max-w-[60px] h-auto" alt="Blueme 로고" />
          </Link>
          <input
            type="email"
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            className="bg-gradient-to-t from-gray-900 tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full  mt-12 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="이메일을 입력해주세요."
          />

          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
            value={password}
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

          <hr className="mt-10" style={{ borderTop: "1px solid gray", width: "100%" }} />
          <div className="text-custom-white mt-10 mb-4 text-left w-full text-2xl font-semibold tracking-tighter">
            SNS 로그인
          </div>

          <button
            onClick={() => (window.location.href = "http://172.30.1.45:8104/oauth2/authorization/kakao")}
            className="
     flex items-center justify-center pl-2 w-full mt-6 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
          >
            <img src={kakao} alt="Kakao logo" className="w-[6%] h-auto mr-1 tracking-tighter" />
            카카오로 로그인
          </button>

          <button
            onClick={() => (window.location.href = "http://172.30.1.45:8104/oauth2/authorization/google")}
            className="
  flex items-center justify-center pl-2 w-full mt-3 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral[200 "
          >
            <img src={google} alt="Google logo" className="mr-1 w-[5%] h-auto tracking-tighter" />
            구글로 로그인
          </button>

          <hr className="mt-10" style={{ borderTop: "1px solid gray", width: "100%" }} />

          <div className="text-custom-white mt-6 text-xl font-semibold tracking-tighter text-center">
            <Link to="/Join">
              <button>회원가입</button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
