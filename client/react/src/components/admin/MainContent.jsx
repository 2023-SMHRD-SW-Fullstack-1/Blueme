import React, {useState, useEffect} from "react";
import WelcomeBanner from "../../partials/dashboard/WelcomeBanner";
import DashboardCard07 from "../../partials/dashboard/DashboardCard07";
import DashboardCard10 from "../../partials/dashboard/DashboardCard10";
import SimpleCard from "../../components/admin/SimpleCard";
import {
  Typography,
} from "@material-tailwind/react";
import axios from "axios";

/*
작성자 : 김혁
작성일 : 2023-10-02
*/
const MainContent = () => {
  const [todayCnt, setTodatCnt] = useState(0);
  const [yesterdayCnt, setYesterdayCnt] = useState(0);
  const [todayRecCnt, setTodayRecCnt] = useState(0);
  const [yesterdayRecCnt, setYesterdayRecCnt] = useState(0);
  const [todayHealthCnt, setTodayHealthCnt] = useState(0);
  const [yesterdayHealthCnt, setYesterdayHealthCnt] = useState(0);
  useEffect(() => {
    const currentDate = new Date();
    const dateTimeString = currentDate.toISOString();
    const yesterdayDate = new Date(); 
    yesterdayDate.setDate(currentDate.getDate() - 1); 
    const yesterdayTimeString = yesterdayDate.toISOString();

    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newclients/${dateTimeString}`)
      .then(response => setTodatCnt(response.data))
      .catch(error => console.error(error));
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newclients/${yesterdayTimeString}`)
      .then(response => setYesterdayCnt(response.data))
      .catch(error => console.error(error));
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newrecmusiclists/${dateTimeString}`)
      .then(response => setTodayRecCnt(response.data))
      .catch(error => console.error(error));
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newrecmusiclists/${yesterdayTimeString}`)
      .then(response => setYesterdayRecCnt(response.data))
      .catch(error => console.error(error));  
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newhealthinfos/${dateTimeString}`)
      .then(response => setTodayHealthCnt(response.data))
      .catch(error => console.error(error)); 
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/admin2/newhealthinfos/${yesterdayTimeString}`)
      .then(response => setYesterdayHealthCnt(response.data))
      .catch(error => console.error(error)); 
  },[])
  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden mt-8 bg-[#f5f7f8]">
      <main>
        
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Welcome banner */}
          <WelcomeBanner />

          {/* Cards */}
          <div className="mb-12 grid gap-y-10 gap-x-6 md:grid-cols-2 xl:grid-cols-3">
            {/* 회원 증가 수 */}
            <SimpleCard
              title="오늘 가입한 회원수"
              color={"bg-green-600"}
              value={todayCnt}
              icon = {<svg xmlns="http://www.w3.org/2000/svg" height="1.5em" viewBox="0 0 448 512" fill="#ffffff">
                        <path d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/>
                      </svg>}
              footer={
                <Typography className="font-normal text-blue-gray-600">
                  <strong className="text-green-500">{(todayCnt - yesterdayCnt) > 0 
                    ? "+" + (todayCnt - yesterdayCnt) 
                    : "-" + (todayCnt - yesterdayCnt)}</strong>
                  &nbsp;than yesterday
                </Typography>
              }
            />
            {/* 추천음악 증가 수 */}
            <SimpleCard
              title="오늘 GPT 추천음악수"
              color={"bg-rose-500"}
              value={todayRecCnt}
              icon = {<svg xmlns="http://www.w3.org/2000/svg" height="1.5em" viewBox="0 0 448 512" fill="#ffffff">
                        <path d="M160 80c0-26.5 21.5-48 48-48h32c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H208c-26.5 0-48-21.5-48-48V80zM0 272c0-26.5 21.5-48 48-48H80c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H48c-26.5 0-48-21.5-48-48V272zM368 96h32c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H368c-26.5 0-48-21.5-48-48V144c0-26.5 21.5-48 48-48z"/>
                      </svg>}
              footer={
                <Typography className="font-normal text-blue-gray-600">
                  <strong className="text-rose-500">{(todayRecCnt - yesterdayRecCnt) > 0 
                    ? "+" + (todayRecCnt - yesterdayRecCnt) 
                    : "-" + (todayRecCnt - yesterdayRecCnt)}</strong>
                  &nbsp;than yesterday
                </Typography>
              }
            />
            {/* 추천음악 증가 수 */}
            <SimpleCard
              title="오늘 스마트워치에서 보낸 데이터 수"
              color={"bg-sky-500"}
              value={todayHealthCnt}
              icon = {<svg xmlns="http://www.w3.org/2000/svg" height="1.5em" viewBox="0 0 512 512" fill="#ffffff">
                        <path d="M96 352V96c0-35.3 28.7-64 64-64H416c35.3 0 64 28.7 64 64V293.5c0 17-6.7 33.3-18.7 45.3l-58.5 58.5c-12 12-28.3 18.7-45.3 18.7H160c-35.3 0-64-28.7-64-64zM272 128c-8.8 0-16 7.2-16 16v48H208c-8.8 0-16 7.2-16 16v32c0 8.8 7.2 16 16 16h48v48c0 8.8 7.2 16 16 16h32c8.8 0 16-7.2 16-16V256h48c8.8 0 16-7.2 16-16V208c0-8.8-7.2-16-16-16H320V144c0-8.8-7.2-16-16-16H272zm24 336c13.3 0 24 10.7 24 24s-10.7 24-24 24H136C60.9 512 0 451.1 0 376V152c0-13.3 10.7-24 24-24s24 10.7 24 24l0 224c0 48.6 39.4 88 88 88H296z"/>
                      </svg>}
              footer={
                <Typography className="font-normal text-blue-gray-600">
                  <strong className="text-sky-500">{(todayHealthCnt - yesterdayHealthCnt) > 0 
                    ? "+" + (todayHealthCnt - yesterdayHealthCnt) 
                    : "-" + (todayHealthCnt - yesterdayHealthCnt)}</strong>
                  &nbsp;than yesterday
                </Typography>
              }
            />
          </div>
          <div className="grid grid-cols-1 gap-6">
            {/* Table (Top Musics) */}
            <DashboardCard07 />
            {/* Card (Customers) */}
            <DashboardCard10 />
          </div>
        </div>
      </main>
    </div>
  );
};

export default MainContent;
