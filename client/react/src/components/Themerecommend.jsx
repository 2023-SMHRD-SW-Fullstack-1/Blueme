import React from "react";
import { Link } from "react-router-dom";

const Themerecommend = () => {
  return (
    <div className="flex flex-col justify-center items-center bg-custom-blue text-custom-white overflow-auto flex-grow">
      <h3 class="font-bold text-4xl sm:text-2xl md:text-2xl sm:mb-10">당신이 선호하는 테마는?</h3>
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 gap-x-6 gap-y-10">
        {Array(4)
          .fill()
          .map((_, i) => (
            <button key={i} class="flex flex-col items-center space-y-4 mt-10">
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-lg w-[150px] h-[150px] object-cover"
              />
              <h5 class="font-semibold">Bernard</h5>
            </button>
          ))}
      </div>
      {/* Add this */}
      <Link to="/Artistrecommend" className="mt-10">
        다음으로 넘어가기
      </Link>
    </div>
  );
};

export default Themerecommend;
