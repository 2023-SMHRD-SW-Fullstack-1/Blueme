import openai
import json
from secrets_1 import api_key

openai.api_key = api_key

#생성된 데이터셋 파일을 openai 에 등록합니다.
# response = openai.File.create(
#   file=open("chat_format_data.jsonl", "rb"),
#   purpose='fine-tune'
# )
# print("File ID:", response['id'])
# print("File size:", response['bytes'])

#findTUned 모델 만들기
# response = openai.FineTuningJob.create(training_file="file-D2pqE7RNR2eAevHnp5lYrGuG", model="gpt-3.5-turbo")

# job_id = response["id"]

# print("Fine-tuning 작업이 생성되었습니다. 작업 ID:", job_id)




# List 10 fine-tuning jobs
# response = openai.FineTuningJob.list(limit=10)
# print(response)

#Retrieve the state of a fine-tune
#print(openai.FineTuningJob.retrieve("ftjob-sZLD662MyK26HcfjhfEQWzTY"))

# response = openai.FineTuningJob.create(
#   training_file='file-17oPwB2NZbwxHK9ZXbgoQnYY',
#   model='gpt-3.5-turbo',
# )

# print("Job ID:", response['id'])

# 질답 확인
# completion = openai.ChatCompletion.create(
#   model="ft:gpt-3.5-turbo:my-org:custom_suffix:id",
#   messages=[
#     {"role": "system", "content": "You are a helpful assistant."},
#     {"role": "user", "content": "Hello!"}
#   ]
# )
# print(completion.choices[0].message)

