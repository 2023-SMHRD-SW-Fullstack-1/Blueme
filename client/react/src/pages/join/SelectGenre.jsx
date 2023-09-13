// SelectArtist.jsx
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const SelectGenre = () => {
  const navigate = useNavigate();
  const [genre, setGenre] = useState([])
  const [checkedState, setCheckedState] = useState([]);


  const handleOnClick = (id) => {
    if(checkedState.includes(id)) {
      setCheckedState((prev) => prev.filter((itemId) => itemId !== id));
    } else if(checkedState.length < 2) {
      setCheckedState((prev) => [...prev, id]);
    } else {
      // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
      setCheckedState(([_, ...rest]) => [...rest, id]);
    }
  };

  useEffect(() => {
    axios.get("http://172.30.1.45:8104/SelectGenre")
    .then((res) => {
      console.log(res);
      setGenre(res.data)
    })
    .catch((err) => console.log(err))
  }, [])

  //회원가입 시 장르 선택
  const handleSelect = () => {
    // const selectedGenres = genre.filter((id) => checkedState);
    localStorage.setItem('selectGenre', checkedState)
    //가수 페이지로 다시 요청
    console.log(checkedState);
    navigate("/Artistrecommend");
  };

  //장르 수정
  const handleUpdate = () => {
   localStorage.setItem('selectGenre', checkedState)
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <h3 class="text-3xl pt-[50px] ">당신이 좋아하는 장르는?</h3>
      <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-5">
        {genre && genre.map((genre, id) => (
          <button
            key={genre.id}
            class="relative flex flex-col items-center space-y-1 mb-5 justify-center"
            onClick={()=> handleOnClick(id)}
          >
            <img
              src={"data:image/;base64," + genre.img}
             
              alt=""
              class="rounded-lg w-[115px] h-[100px] h-auto object-cover blur-[1.5px]"
            />
            <p className="absolute text-xl">{genre.name}</p>

            {checkedState.includes(id) && (
              <span className="absolute top-[20%] left-[40%] text-5xl font-bold text-black">
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
        text-[16px]"
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
            text-[16px]"
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
