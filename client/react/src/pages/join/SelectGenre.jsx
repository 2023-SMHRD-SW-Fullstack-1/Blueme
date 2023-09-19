/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 장르 선택 및 장르 수정 기능
*/
// SelectArtist.jsx
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";

const SelectGenre = () => {
  const navigate = useNavigate();
  const [genre, setGenre] = useState([])
  const [checkedState, setCheckedState] = useState([]);
  const [page, setPage] = useState(1); //페이징 관련
  const [genres, setGenres] = useState([])//페이징 관련
  let id = localStorage.getItem('id')
  const user = useSelector(state => state.memberReducer.user)
  const location = useLocation()
  const urlParams = new URLSearchParams(location.search);
  const snsId = urlParams.get('id');
  if(localStorage.getItem('id') === null) {
    id = localStorage.setItem('id', snsId)
  }
  // const sns = localStorage.setItem('id', snsId)

   //3초 로딩 함수
   const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")
    }, 3000);// 원하는 시간 ms단위로 적어주기
    };

    //장르 한 개 선택 시
    const handleOnClick = (id) => {
      const genreId = genre[id].genreId
      // console.log(genreId)
      if(checkedState.includes(genreId)) {//같은 장르 id고르면 아닌 것만 추가
        setCheckedState((prev) => prev.filter((itemId) => itemId !== genreId));
      } else if(checkedState.length < 2) { //2개 이하로 고른 경우 추가
        setCheckedState((prev) => [...prev, genreId]);
      } else {
        // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
        setCheckedState(([_, ...rest]) => [...rest, genreId]);
      }
    };
    
    //화면 로딩 시 장르 데이터 불러오기
    useEffect(() => {
      axios
      .get("http://172.30.1.45:8104/SelectGenre") 
      .then((res) => {
        console.log(res);
        // console.log('userid', id);
        setGenre(res.data)
      })
      .catch((err) => console.log(err))
    }, [])
      // console.log(checkedState);

    // //카카오 로그인

    // e.preventDefault()
    // dispatch(loginRequest());
    // window.location.href = "http://localhost:8104/oauth2/authorization/kakao"
    // console.log('params', params);
    // const requestData = {
    //   email: email,
    //   password: password,
    // };
    // await axios
    // .post("http://172.30.1.45:8104/login", requestData)
    // .then((res) => {
      
    //   console.log(res);
    //   let accessToken = res.headers.get("Authorization");
    //   let refreshToken = res.headers["authorization-refresh"];
    //   localStorage.setItem("accessToken", accessToken);
    //   localStorage.setItem("refreshToken", refreshToken);
    //   localStorage.setItem("id", res.data.id);
    //    dispatch(loginSuccess(res.data))
    //    navigate("/");
    //   })
    // .catch(err => {
    //   console.log(err)
    //   dispatch(loginFailure(err.message))
    // })
    

    //회원가입 시 장르 선택(2개)
    const handleSelect = () => {
          if(checkedState.length === 2) {
          let requestData = 0
            if(!location.search) {
                requestData = {genreIds : checkedState ,favChecklistId : id}
                console.log(requestData);
            } else {
                requestData = {genreIds : checkedState ,favChecklistId : snsId}// url로 넘어오는 snsId가져오기
            }
          console.log(requestData);
              axios
              .post("http://172.30.1.45:8104/SaveFavGenre",requestData)//선택한 장르 저장 성공 여부
              .then((res) => {//저장 실패일 경우 반환값 -1
                if(res.data > 0) {
                  navigate('/Artistrecommend')
                }else if(res.data == -1) {
                  alert('저장되지 않았습니다. 다시 선택해주세요.')
                  navigate('./SelectGenre')
                }   
                console.log(res)
              })
              .catch((err) => console.log(err))
        } else{
            document.getElementById('toast-warning').classList.add("reveal")
            timeout()
            navigate('/SelectGenre')
        }
    };
    //장르 수정하기
    const handelUpdate = () => {
      if(checkedState.length === 2) {
        let requestData = 0
        if(!location.search) {
            requestData = {genreIds : checkedState ,favChecklistId : id}
            console.log(requestData);
        } else {
            requestData = {genreIds : checkedState ,favChecklistId : snsId}// url로 넘어오는 snsId가져오기
        }
          console.log('requestData',requestData);
          axios
          .patch("http://172.30.1.45:8104/updateFavGenre",requestData)//선택한 장르 저장 성공 여부
          .then((res) => {//저장 실패일 경우 반환값 -1
            if(res.data >0) {
              navigate('/MyPage')
            }else if(res.data == -1) {
              alert('저장되지 않았습니다. 다시 선택해주세요.')
              navigate('./SelectGenre')
            }   
            console.log(res)
          })
          .catch((err) => console.log(err))
          console.log(checkedState);
    } else{
        document.getElementById('toast-warning').classList.add("reveal")
        timeout()
        navigate('/SelectGenre')
    }
    }

  

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tight h-auto text-custom-white p-3">
      <h3 className="text-3xl pt-[80px] ">당신이 좋아하는 장르는?</h3>
      <div className="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-8">
        {genre && genre.map((genre, genreId, i) => (
          <button
            key={genre.genreId}
            className="relative flex flex-col items-center space-y-1 mb-5 justify-center"
            onClick={()=> handleOnClick(genreId)}
          >
            <img
              src={"data:image/;base64," + genre.img}
              alt="genre img"
              className="rounded-lg w-[180px] h-[175px] object-cover blur-[1.5px]"
            />
            <p className="absolute text-3xl">{genre.name}</p>
            {checkedState.includes(parseInt(genreId)+1) && (
              <span className="absolute top-[25%] left-[40%] text-7xl font-bold text-black">
                ✔
              </span>
            )}
          </button>
        ))}
      </div>
      {user.email === null ?
      <button
      onClick={handleSelect}
      className="
        mt-2
        mb-10
        w-full
        px-3 h-10 relative 
        bg-[#221a38]  
        rounded-lg border border-soild border-[#fdfdfd]
        text-custom-white
        text-[16px]
        mb-[80px]"
    >
      선택하기
    </button> : <button
          onClick={handelUpdate}
          className="
            mt-2
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]
            mb-[80px]"
        >
          수정하기
        </button>}

        {/* 토스트 창 띄우기 */}
        <div className="flex justify-center items-center">
          <div id="toast-warning" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
            <div className="ml-3 font-normal text-center">장르 2개를 선택해주세요.</div>
          </div>
        </div>
     
    </div>
  );
};

export default SelectGenre;

// const handleOnClick = (index) => {
//     const checkedCount = checkedState.filter(Boolean).length;
//     if (checkedCount < 2 || checkedState[index]) {
//     setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
//     }
//     //