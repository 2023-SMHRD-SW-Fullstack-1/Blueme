import React from 'react';
import { Link } from 'react-router-dom';

import kakao from '../../assets/img/kakao.png';
import google from '../../assets/img/google.png';

const Join = () => {
    return (
        <div className=" bg-custom-blue flex flex-col items-center justify-center px-4 sm:px-8 md:px-16  ">
            <div className="mt-5 text-custom-white w-full mb-3 text-left sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
                Blueme 일반 계정 회원가입
            </div>
            <input
                type="email"
                className="focus:border-custom-white pl-2 w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
                placeholder="이메일을 입력해주세요."
            />
            <input
                type="password"
                className="focus:border-custom-white pl-2 text-custom-white w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
                placeholder="비밀번호를 입력해주세요."
            />
            <input
                type="password"
                className="focus:border-custom-white pl-2 text-custom-white w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
                placeholder="비밀번호를 다시 입력해주세요."
            />
            <input
                type="text"
                className="focus:border-custom-white pl-2 w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
                placeholder="닉네임을 입력해주세요."
            />

            <button
                className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white"
            >
                회원가입
            </button>
            <hr className="mt-10 mb-3" style={{ borderTop: '1px solid gray', width: '100%' }} />
            <div className="text-custom-white mt-5 mb-4 text-left w-full ">SNS계정 회원가입</div>
            <button
                className="
     flex items-center justify-center pl-2 w-full mt-2 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            >
                <img src={kakao} alt="" className="w-[6%] h-auto mr-1" />
                카카오로 로그인
            </button>
            <button
                className="
  flex items-center justify-center pl-2 w-full mt-3 border border-soild border-custom-white rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
            >
                <img src={google} alt="Google logo" className="mr-1 w-[5%] h-auto" />
                구글로 로그인
            </button>
            <div className="pb-10"></div>
        </div>
    );
};

export default Join;
