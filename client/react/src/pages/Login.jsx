/*
작성자: 이유영
날짜(수정포함): 2023-09-11
설명: 로그인 구현, 회원 리덕스
*/
/*
작성자: 신지훈
날짜(수정포함): 2023-09-14
설명: 로그인 반응형 구현
*/
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import logo2 from "../assets/img/logo2.png";
import kakao from "../assets/img/kakao.png";
import google from "../assets/img/google.png";
import { loginRequest, loginSuccess, loginFailure } from "../store/member/memberAction";
import { useDispatch, useSelector } from "react-redux";
import "../App.css";

axios.defaults.withCredentials = true;

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = useParams();
  const user = useSelector((state) => state.memberReducer.user);
  const id = user.id;

  //일반 로그인
  const handleLogin = async (e) => {
    e.preventDefault();
    dispatch(loginRequest());
    const requestData = {
          email: email,
          password: password,
        };
    const headers = {
      'content-type' : 'application/json'
    }
        // console.log(requestData);
      await axios
      .post(`http://localhost:8104/login`, requestData)
      .then((res) => {
        console.log(res);
        const acToken = res.data.accessToken
        const reToken = res.data.refreshToken
        axios.defaults.headers.common['Authoriztion'] = `Bearer ${acToken}`
        axios.defaults.headers.common['Authoriztion-refresh'] = `Bearer ${reToken}`
        // console.log("axiosHeaders : ",axios.defaults.headers);
        let accessToken = "Bearer "+res.headers.get("Authorization");
        let refreshToken = "Bearer "+res.headers["authorization-refresh"];
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("id", res.data.id);
        localStorage.setItem("img", res.data.img_url);
        dispatch(loginSuccess(res.data));
        navigate("/");
      })
      .catch((err) => {
        console.log(err);
        document.getElementById("toast-warning").classList.add("reveal");
        timeout();
        dispatch(loginFailure(err.message));
      });
  };

  //3초 로딩 함수
  const timeout = () => {
    setTimeout(() => {
      document.getElementById("toast-warning").classList.remove("reveal");
      navigate("/Login");
    }, 2000); // 원하는 시간 ms단위로 적어주기
  };

  return (
    <div className=" min-h-screen bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 flex flex-col px-4 sm:px-8 md:px-16 overflow-scroll">
      {/* <div className="mt-36 text-custom-white mb-3 text-center sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4"></div> */}
      <br /><br />
      <div className="flex items-center justify-center h-screen w-full p-4 mt-10">
        <div className="w-[500px]">
          <Link to="/Main" className="self-center flex items-center justify-center">
            <span className="text-3xl tracking-tight text-[rgba(255,255,255,0.80)]">Blueme</span>
            <img src={logo2} className="w-full max-w-[60px] h-auto" alt="Blueme 로고" />
          </Link>
          <input
            type="email"
            onChange={(e) => setEmail(e.target.value)}
            value={email}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-12 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            placeholder="이메일을 입력해주세요."
          />

          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
            value={password}
            className="bg-gradient-to-t from-gray-900 tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 text-custom-white w-full mt-3 rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
            placeholder="비밀번호를 입력해주세요."
          />

          <button
            onClick={handleLogin}
            className="
             mt-10
             h-10
             px-3 relative 
             bg-[#221a38]  
             rounded-lg border border-[#fdfdfd]
             text-custom-white
             tracking-tight
             md:w-full w-full"
          >
            로그인
          </button>

          <hr className="mt-10" style={{ borderTop: "1px solid gray", width: "100%" }} />
          <div className="text-custom-white mt-10 mb-4 text-left w-full tracking-tight">SNS 로그인</div>

          <button
            onClick={() => {
              window.location.href = "http://localhost:8104/oauth2/authorization/kakao";
            }}
            className="
     flex items-center justify-center pl-2 w-full mt-6 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white text-sm peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
          >
            <img src={kakao} alt="Kakao logo" className="w-[4%] h-auto mr-1 tracking-tight" />
            카카오로 로그인
          </button>

          <button
            onClick={() => {
              window.location.href = "http://localhost:8104/oauth2/authorization/google";
            }}
            className="
  flex items-center justify-center pl-2 w-full mt-3 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white text-sm peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral[200 "
          >
            <img src={google} alt="Google logo" className="mr-1 w-[4%] h-auto tracking-tight" />
            구글로 로그인
          </button>

          <hr className="mt-10 border-t border-gray-500 w-full" />

          <div className="text-custom-white mt-6 tracking-tight text-center mb-20">
            <Link to="/Join">
              <button>Blueme 회원가입</button>
            </Link>
          </div>
        </div>
      </div>
      {/* 토스트 창 띄우기 */}
      <div className="flex justify-center items-center">
        <div
          id="toast-warning"
          className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800"
          role="alert"
        >
          <div className="ml-3 font-normal text-center">로그인에 실패하셨습니다.</div>
        </div>
      </div>
    </div>
  );
};

export default Login;
