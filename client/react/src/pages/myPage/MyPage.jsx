import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import user from "../../assets/img/defalut.png";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import { useLocation } from "react-router-dom";

const MyPage = () => {
  const [singers, setSingers] = useState([]);
  const location = useLocation();
  const selectedArtistsFromRecommendation =
    location.state?.selectedArtists || [];

  useEffect(() => {
    // 가상의 가수 데이터
    const dummySingers = [
      {
        id: 1,
        name: "Adele",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
      {
        id: 2,
        name: "Ed Sheeran",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
      {
        id: 3,
        name: "Taylor Swift",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
    ];

    // Merge dummySingers and selectedArtistsFromRecommendation
    setSingers([...dummySingers, ...selectedArtistsFromRecommendation]);
  }, [selectedArtistsFromRecommendation]);
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 flex flex-col min-h-screen text-2xl font-semibold tracking-tighter">
      <div className="flex justify-between items-center mt-20">
        <div className="flex items-center boar">
          <img
            src={user}
            alt=""
            className="w-[70px] h-[70px] mb-3 mt-2 rounded-lg"
          />
          <span className="pt-1 ml-3 text-[20px] ">닉네임</span>
        </div>
        <div className="mt-3 ">
          <Link to="/MemberInfoChange">
            <button className=" bg-gradient-to-t from-gray-600 rounded-2xl text-sm h-8 w-20 p-1">
              내 프로필
            </button>
          </Link>
        </div>
      </div>

      <div className="text-2xl mt-6">내 정보</div>

      <div className="mt-2 sm:mt-2 md:mt-2">
        <p className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">blueme123@gmail.com</p>
      </div>
      <div className="mt-2 sm:mt-2 md:mt-2 ">
      <p className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tighter border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-[386px] mt-3 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200">닉네임</p>
        <div className="mt-10 ">
          <div className="text-2xl">당신이 좋아하는 음악장르는?</div>
          <div className="flex w-full mt-5 text-base">
            <Swiper
              spaceBetween={20}
              slidesPerView={2}
              onSlideChange={() => console.log("slide change")}
              onSwiper={(swiper) => console.log(swiper)}
              className="w-[280px] flex"
            >
              {singers.map((singer) => (
                <SwiperSlide key={singer.id}>
                  <button class="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                    <img
                      src={singer.image}
                      alt="singer image"
                      class="rounded-lg border object-cover blur-sm"
                    />
                    <h5 className="font-semibold absolute">{singer.name}</h5>
                  </button>
                </SwiperSlide>
              ))}
            </Swiper>
            <Link
              to="/SelectGenre"
              className="text-custom-lightgray w-30 ml-5 mr-8 self-center text-byte"
            >
              더보기
            </Link>
          </div>
        </div>
        <div className="text-2xl mt-5">당신이 좋아하는 아티스트는?</div>
        <div className="flex items-start w-full mt-5 text-base">
          <Swiper
            spaceBetween={20}
            slidesPerView={2} // Reduce the number of slides per view
            onSlideChange={() => console.log("slide change")}
            onSwiper={(swiper) => console.log(swiper)}
            className="w-[280px] flex"
          >
            {singers.map((singer) => (
              <SwiperSlide key={singer.id}>
                <button class="flex flex-col items-center text-center space-y-4 mt-2 justify-center">
                  <img
                    src={singer.image}
                    alt=""
                    class="rounded-lg img-fixed border object-cover blur-sm"
                  />
                  <h5 class="font-semibold absolute">{singer.name}</h5>
                </button>
              </SwiperSlide>
            ))}
          </Swiper>
          <Link
            to="/Artistrecommend"
            className="text-custom-lightgray w-30 ml-5 mr-8 self-center text-byte"
          >
            더보기
          </Link>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
