/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 장르 선택 및 장르 수정 기능
*/
// SelectArtist.jsx
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";
import Genre from '../../components/MyPage/Genre'

const SelectGenre = () => {
  const navigate = useNavigate();
  const [genre, setGenre] = useState([])//전체 장르
  const [checkedState, setCheckedState] = useState([]);//선택 장르
  let id = localStorage.getItem('id')
  const user = useSelector(state => state.memberReducer.user)//memeber리덕스에서 user정보 가져오기
  const location = useLocation()
  const urlParams = new URLSearchParams(location.search);//쿼리스트링에서
  const snsId = urlParams.get('id');//키값이 id인 params가져오기
  if(localStorage.getItem('id') === null) {
    id = localStorage.setItem('id', snsId)
  }


   //3초 로딩 함수
   const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")//토스트창 소명
    }, 1000);// 원하는 시간 ms단위로 적어주기
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
      .get(`${process.env.REACT_APP_API_BASE_URL}/SelectGenre`) //장르 전체 데이터 불러오기
      .then((res) => {
        // console.log(res);
        // console.log('userid', id);
        setGenre(res.data)//전체 장르
      })
      .catch((err) => console.log(err))
    }, [])


    //회원가입 시 장르 선택(2개)
    const handleSelect = () => {
          if(checkedState.length === 2) {
          let requestData = 0
            if(!location.search) {
                requestData = {genreIds : checkedState ,favChecklistId : id}
                // console.log(requestData);
            } else {
                requestData = {genreIds : checkedState ,favChecklistId : snsId}// url로 넘어오는 snsId가져오기
            }
          console.log(requestData);
              axios
              .post(`${process.env.REACT_APP_API_BASE_URL}/SaveFavGenre`,requestData)//선택한 장르 저장 성공 여부
              .then((res) => {//저장 실패일 경우 반환값 -1
                if(res.data > 0) {
                  navigate('/Artistrecommend')
                }else if(res.data == -1) {
                  alert('저장되지 않았습니다. 다시 선택해주세요.')
                  navigate('./SelectGenre')
                }   
                // console.log(res)
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
            // console.log(requestData);
        } else {
            requestData = {genreIds : checkedState ,favChecklistId : snsId}// url로 넘어오는 snsId가져오기
        }
          // console.log('requestData',requestData);
          axios
          .patch(`${process.env.REACT_APP_API_BASE_URL}/updateFavGenre`,requestData)//선택한 장르 저장 성공 여부
          .then((res) => {//저장 실패일 경우 반환값 -1
            if(res.data >0) {
              navigate('/MyPage')
            }else if(res.data == -1) {
              alert('저장되지 않았습니다. 다시 선택해주세요.')
              navigate('./SelectGenre')
            }   
            // console.log(res)
          })
          .catch((err) => console.log(err))
          // console.log(checkedState);
    } else{
        document.getElementById('toast-warning').classList.add("reveal")
        timeout()
        navigate('/SelectGenre')
    }
    }

  

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 tracking-tight h-auto text-custom-white p-3">
      <h3 className="text-2xl pt-[90px] xl:ml-[100px] xl:mr-[100px]">당신이 좋아하는 장르는?</h3>
      <div className="mb-[100px] grid md:grid-cols-4 sm:grid-cols-2 xs:grid-cols-2 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-8">
        {genre && genre.map((genre, genreId, i) => (
          <button
            key={genre.genreId}
            className="relative flex flex-col items-center space-y-1 mb-5 justify-center"
            onClick={()=> handleOnClick(genreId)}
          >
            <Genre key={genre.genreId} genre={genre} checkedState={checkedState} genreId={genreId}/>
          </button>
        ))}
        
      </div>
      {user.email === null ?
      <button
      onClick={handleSelect}
      className="
          mt-5
          w-full
          px-3 h-[60px]
          bg-[#221a38]  
          rounded-lg border border-soild border-[#fdfdfd]
          text-custom-white
          text-[20px]
          mb-[65px]
          bottom-0
          fixed left-0 right-0 mx-auto
        "
    >
      선택하기
    </button> : <button
          onClick={handelUpdate}
          className="
              mt-5
              w-full
              px-3 h-[60px]
              bg-[#221a38]  
              rounded-lg border border-soild border-[#fdfdfd]
              text-custom-white
              text-[20px]
              mb-[65px]
              bottom-0
              fixed left-0 right-0 mx-auto
            "
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