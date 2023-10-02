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
  },[])
  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden mt-8 bg-[#f5f7f8]">
      <main>
        
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Welcome banner */}
          <WelcomeBanner />

          {/* Cards */}
          <div className="mb-12 grid gap-y-10 gap-x-6 md:grid-cols-2 xl:grid-cols-4">
              <SimpleCard
                title="New clients"
                color="black"
                value={todayCnt}
                footer={
                  <Typography className="font-normal text-blue-gray-600">
                    <strong className="text-green-500">{(todayCnt - yesterdayCnt) > 0 
                      ? "+" + (todayCnt - yesterdayCnt) 
                      : "-" + (todayCnt - yesterdayCnt)}</strong>
                    &nbsp;than yesterday
                  </Typography>
                }
              />

          </div>
          <div className="grid grid-cols-12 gap-6">
            
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
