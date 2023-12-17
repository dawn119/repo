#!/bin/bash

# 检查参数数量
if [ "$#" -ne 3 ]; then
    echo "用法: $0 文件路径 搜索内容 输出文件"
    exit 1
fi

# 获取参数
file_path=$1
search_content=$2
output_file=$3

# 检查文件是否存在
if [ ! -f "$file_path" ]; then
    echo "错误: 文件 '$file_path' 不存在"
    exit 1
fi

# 执行搜索并保存结果到文件
grep -n "$search_content" "$file_path" > "$output_file"

# 将结果输出到控制台
cat "$output_file"

echo "搜索结果已保存到文件 '$output_file'"
