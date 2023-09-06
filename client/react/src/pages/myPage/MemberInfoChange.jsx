import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo2 from "../../assets/img/logo2.png";
import axios from "axios";

function MemberInfoChange() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");

  const handleUpdate = async () => {
    if (password !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      // 서버에 PUT 요청을 보내 데이터 업데이트
      const response = await axios.patch(
        "user/update",

        {
          email,
          password,
          nickname,
        },
        {
          // headers: { Authorization: `Bearer ${token}` },
        }
      );
      console.log(response);
      console.log("Sending request with email:", email);
      console.log("Sending request with email:", password);

      alert("회원 정보가 성공적으로 수정되었습니다.");
    } catch (error) {
      console.error(error);
      alert("회원 정보 수정에 실패했습니다.");
    }
  };

  return (
    <div className=" bg-custom-blue flex flex-col items-center justify-center px-4 sm:px-8 md:px-16 min-h-screen ">
      <div className="mt-5 text-custom-white mb-3 text-center  sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
        <Link to="/Main" className="self-center flex flex-col items-center justify-center">
          <img src={logo2} className="w-full max-w-[100px] h-auto " alt="" />
          <span className="text-xl text-[rgba(255,255,255,0.80)]">닉-네임</span>
        </Link>
      </div>
      <div className="text-xl text-custom-white te mt-5 mb-4 text-left w-full">내 정보</div>
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

      <input
        type="password"
        onChange={(e) => setConfirmPassword(e.target.value)}
        className="focus:border-custom-white pl-2 w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
        placeholder="비밀번호를 다시 입력해주세요.."
      />
      <input
        type="text"
        onChange={(e) => setNickname(e.target.value)}
        className="focus:border-custom-white pl-2 text-custom-white w-full mt-2 border border-soild border-[rgba(253,253,253,0.10)] rounded-lg bg-custom-blue text-whitepeer block min-h-auto bg-transparent py-[0.32rem] leading-[2.15] outline-none transition-all duration[200 ease-linear motion-reduce-transition-none dark:text-neutral[200 "
        placeholder="로그인닉네임 가져올 예정"
      />

      <button
        className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white"
        onClick={handleUpdate}
      >
        수정하기
      </button>
      <Link to="/MemberDelete">
        <div className="text-custom-gray mt-5 mb-5 text-sm">탈퇴하기</div>
      </Link>
    </div>
  );
}

export default MemberInfoChange;
