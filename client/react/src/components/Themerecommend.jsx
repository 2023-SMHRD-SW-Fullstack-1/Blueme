import React from "react";
import { Link } from "react-router-dom";

const Themerecommend = () => {
  return (
    <div className="flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10">당신이 선호하는 테마는?</h3>
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 ">
        {Array(4)
          .fill()
          .map((_, i) => (
            <button
              key={i}
              class="w-full h-full flex flex-col items-center justify-center p-8 bg-white rounded-lg shadow-md"
            >
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-lg w-[150px] h-[150px] object-cover"
              />
              <h5 class="font-semibold">Bernard</h5>
            </button>
          ))}
      </div>
      <div className="w-full h-auto rounded-lg p-4">
        <Link to="/Main" className="mb-10">
          <button
            className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white"
          >
            수정하기
          </button>
        </Link>
      </div>
    </div>
  );
};

export default Themerecommend;
