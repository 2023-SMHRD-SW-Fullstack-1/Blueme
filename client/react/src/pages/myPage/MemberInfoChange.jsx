import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo2 from "../../assets/img/logo2.png";
import axios from "axios";
import user from '../../assets/img/defalut.png'
import { useDispatch } from "react-redux";

function MemberInfoChange() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");

  const dispatch = useDispatch()

  const navigate = useNavigate()


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
    <div className=" bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 flex flex-col px-4 sm:px-8 md:px-16 min-h-screen font-semibold tracking-tighter">
      <div className="mt-36 text-custom-white mb-3 text-center  sm:w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4">
        <Link to="/Main" className="self-center flex flex-col items-center justify-center">
          <img src={user} className="w-[120px] h-[120px] rounded-xl " alt="" />
          <span className="text-xl mt-3">닉네임</span>
        </Link>
      </div>
      <form>
      <div className="text-2xl text-custom-white te mt-5 text-left w-full">내 정보</div>
      <input
        type="email"
        onChange={(e) => setEmail(e.target.value)}
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        value="blueme123@gmail.com"
      />
      <input
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="비밀번호를 입력해주세요."
      />

      <input
        type="password"
        onChange={(e) => setConfirmPassword(e.target.value)}
        className="bg-gradient-to-t from-gray-900 tracking-tighter h-[45px] text-base border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="비밀번호를 다시 입력해주세요.."
      />
      <input
        type="text"
        onChange={(e) => setNickname(e.target.value)}
        className="bg-gradient-to-t from-gray-900 tracking-tighter h-[45px] text-base border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
        placeholder="로그인닉네임 가져올 예정"
      />

      <button
        className="
            mt-10
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]"
        onClick={handleUpdate}
      >
        수정하기
      </button>
      </form>
      <Link to="/MemberDelete">
        <div className="text-custom-gray mt-6 text-sm text-center">탈퇴하기</div>
      </Link>
      <Link to="/MemberDelete">
        <div className="text-custom-gray mt-6 text-sm text-center">로그아웃</div>
      </Link>
      
    </div>
  );
}

export default MemberInfoChange;
