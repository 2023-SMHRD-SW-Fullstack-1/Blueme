/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 장르 선택 및 장르 수정 기능
*/
// SelectArtist.jsx
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const SelectGenre = () => {
  const navigate = useNavigate();
  const [genre, setGenre] = useState([])
  const [checkedState, setCheckedState] = useState([]);
  const [page, setPage] = useState(1); //페이징 관련
  const [genres, setGenres] = useState([])//페이징 관련
  const id = localStorage.getItem('id')
  console.log(localStorage.getItem('email'));

    //장르 한 개 선택 시
    const handleOnClick = (id) => {
      const genreId = genre[id].genreId
      // console.log(genreId)
      if(checkedState.includes(genreId)) {
        setCheckedState((prev) => prev.filter((itemId) => itemId !== genreId));
      } else if(checkedState.length < 2) {
        setCheckedState((prev) => [...prev, genreId]);
      } else {
        // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
        setCheckedState(([_, ...rest]) => [...rest, genreId]);
      }
    };
    
    //화면 로딩 시 장르 데이터 불러오기
    useEffect(() => {
      axios.get("http://172.30.1.45:8104/SelectGenre")
      .then((res) => {
        console.log(res);
        setGenre(res.data)
      })
      .catch((err) => console.log(err))
    }, [])
      console.log(checkedState);

    //회원가입 시 장르 선택(2개)
    const handleSelect = () => {
          if(checkedState.length === 2) {
              // const selectedGenres = genre.filter((id) => checkedState);
              localStorage.setItem('selectGenre1', checkedState[0])
              localStorage.setItem('selectGenre2', checkedState[1])
              // localStorage.setItem('selectGenre', JSON.stringify(checkedState));
              const requestData = {genreIds : checkedState ,favChecklistId:id}
              console.log(requestData);
              axios.post("http://172.30.1.45:8104/SaveFavGenre",requestData)
              .then((res) => {
                if(res.data >0) {
                  navigate('/Artistrecommend')
                }else if(res.data == -1) {
                  alert('저장되지 않았습니다. 다시 선택해주세요.')
                  navigate('./SelectGenre')
                }   
                console.log(res)
              }).catch((err) => console.log(err))
              console.log(checkedState);
        } else{
            alert('선호하는 장르 2개를 선택해주세요.')
            navigate('/SelectGenre')
        }
    };

  //장르 수정
  const handleUpdate = () => {
    localStorage.setItem('selectGenre1', checkedState[0])
    localStorage.setItem('selectGenre2', checkedState[1])
  };

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
              className="rounded-lg w-[180px] h-[175px] h-auto object-cover blur-[1.5px]"
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
      {localStorage.getItem('email') === null ?
      <button
      onClick={handleSelect}
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
      선택하기
    </button> : <button
          onClick={handleUpdate}
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