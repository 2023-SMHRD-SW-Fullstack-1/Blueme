/*
작성자: 신지훈
날짜: 2023-09-11
설명: 회원정보 수정화면, 사용자 프로필사진 등록기능 추가
*/
/*
작성자: 이유영
날짜: 2023-09-16
설명: 회원정보 수정 기능(+ 리덕스 )/토스트 창
*/

import React, { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import user from "../../assets/img/defalut.png";
import { useDispatch, useSelector } from "react-redux";
import { userUpdate } from "../../store/member/memberAction";
import '../../App.css'
import basicProfile from '../../assets/img/basicProfile.jpg'

function MemberInfoChange() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [imgFile, setImgFile] = useState("");
  const imgRef = useRef();
  const previewRef = useRef();
  const dispatch = useDispatch()
  const user = useSelector(state => state.memberReducer.user)
  const id = user.id
 


  // 선택된 이미지 파일을 저장하는 함수
  const saveImgFile = () => {
    if (!imgRef.current.files[0]) {
      console.log("파일을 선택해주세요");
      return;
    }
    const file = imgRef.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      const base64String = reader.result
      const trimmedBase64String = base64String.substring(base64String.indexOf(",") + 1);
      // console.log(trimmedBase64String);   
      setImgFile(trimmedBase64String);
      previewRef.current.src = reader.result;
     
    };
   
  };
  // console.log("img", imgFile);

  //3초 로딩 함수
  const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")
      // document.getElementById('password').classList.remove("reveal")
      navigate("/MyPage");
    }, 2000);// 원하는 시간 ms단위로 적어주기
  };

  const handlePw = (e) => {
    // if(e.target.value === "") {
    //   document.getElementById('password').classList.add("reveal")
    //   timeout()
    // }
  }

  //회원정보 수정
  const handleUpdate = async (e) => {
    // let storageEmail = localStorage.getItem("email");
    e.preventDefault();
    const requestData = { id: id, email: user.email, password: password, nickname: nickname, img_url : imgFile };
    console.log('requestData', requestData);
    await axios
      .patch(`http://172.30.1.45:8104/user/update`, requestData)
      .then((res) => {
        dispatch(userUpdate(requestData))
        localStorage.setItem("password", password);
        document.getElementById('toast-warning').classList.add("reveal")
        timeout()
        console.log(res);
      })
      .catch((err) => console.log(err));
  };
  

  return (
    <div className=" bg-gradient-to-t from-gray-950 via-stone-950 to-gray-700 flex flex-col px-4 sm:px-8 md:px-16 min-h-screen tracking-tight">
      <div className="mt-36 text-custom-white mb-3 text-center  ">
        <div className="self-center flex flex-col items-center justify-center">
          <label htmlFor="profileImg">
          {user.img_url === null ?
            <img
              // src={imgFile ? imgFile : `data:image/;base64,${myFeed[0]?.myFeed.mem_photo}`}
              src={basicProfile}
              alt=""
              ref={previewRef}
              onChange={saveImgFile}
              //사진 꾸미기
              style={{
                width: "100px",
                height: "100px",
                borderRadius: "10%",
                objectFit: "cover",
                cursor: "pointer",
              }}
            /> : 
            <img
              // src={imgFile ? imgFile : `data:image/;base64,${myFeed[0]?.myFeed.mem_photo}`}
              src={"data:image/;base64," + user.img_url}
              alt=""
              ref={previewRef}
              onChange={saveImgFile}
              //사진 꾸미기
              style={{
                width: "100px",
                height: "100px",
                borderRadius: "10%",
                objectFit: "cover",
                cursor: "pointer",
              }}
            /> }
          </label>

          <input
            className="hidden"
            type="file"
            accept="image/*"
            id="profileImg"
            onChange={saveImgFile}
            ref={imgRef}
            name="b_file"
          />
          <span className="text-xl mt-3">{user.email}</span>
        </div>
      </div>
      {/* 회원정보 수정하기 */}
      <div className="flex items-center justify-center  w-full p-4">
        <div className="w-full md:w-1/2 lg:w-1/4 ">
          <div className="text-2xl text-custom-white te mt-5 text-left w-full">회원 정보 수정</div>
          <div className="mt-2 sm:mt-2 md:mt-2 ">
          <p className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">
          {user.email}</p></div>
          <input
            type="password"
            onChange={handlePw}
            className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
            placeholder="비밀번호를 입력해주세요."
          />

          <input
            type="password"
            onChange={() => {}}
            className="bg-gradient-to-t from-gray-900 tracking-tight h-[45px] text-base border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
            placeholder="비밀번호를 다시 입력해주세요."
          />
          <input
            type="text"
            onChange={(e) => setNickname(e.target.value)}
            className="bg-gradient-to-t from-gray-900 tracking-tight h-[45px] text-base border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
            defaultValue={user.nickname}
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

          <div className="flex justify-center mt-6 ">
            <button
              className="text-custom-gray text-sm text-center"
              onClick={() => {
                navigate("/MemberDelete");
              }}
            >
              탈퇴하기
            </button>
          </div>
        </div>
      </div>
        {/* 토스트 창 띄우기 */}
        <div className="flex justify-center items-center">
          <div id="toast-warning" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
            <div className="ml-3 font-normal text-center">수정이 완료되었습니다.</div>
          </div>
        </div>
         {/* 토스트 창 띄우기 */}
        <div className="flex justify-center items-center">
          <div id="password" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
            <div className="ml-3 font-normal text-center">비밀번호를 입력해주세요!</div>
          </div>
        </div>
    </div>
  );
}

export default MemberInfoChange;
