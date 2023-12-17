#!/bin/bash

# ����������
if [ "$#" -ne 3 ]; then
    echo "�÷�: $0 �ļ�·�� �������� ����ļ�"
    exit 1
fi

# ��ȡ����
file_path=$1
search_content=$2
output_file=$3

# ����ļ��Ƿ����
if [ ! -f "$file_path" ]; then
    echo "����: �ļ� '$file_path' ������"
    exit 1
fi

# ִ�����������������ļ�
grep -n "$search_content" "$file_path" > "$output_file"

# ��������������̨
cat "$output_file"

echo "��������ѱ��浽�ļ� '$output_file'"
