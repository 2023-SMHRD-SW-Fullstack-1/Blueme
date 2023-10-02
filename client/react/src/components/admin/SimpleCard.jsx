import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
} from "@material-tailwind/react";

import React from 'react'

const SimpleCard = ({ color, icon, title, value, footer }) => {
  return (
    <Card>
      <CardHeader
        variant="gradient"
        className={`absolute -mt-4 grid h-16 w-16 place-items-center ${color}`}
      >
        {icon}
      </CardHeader>
      <CardBody className="p-4 text-right">
        <Typography variant="small" className="font-normal text-blue-gray-600">
          {title}
        </Typography>
        <Typography variant="h4" color="blue-gray">
          {value}
        </Typography>
      </CardBody>
      {footer && (
        <CardFooter className="border-t border-blue-gray-50 p-4">
          {footer}
        </CardFooter>
      )}
    </Card>
  )
}

export default SimpleCard