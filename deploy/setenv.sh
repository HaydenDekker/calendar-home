cd ~/Documents/auto/deploy

# The user must be added to visudo with
#  your_username ALL=(ALL) NOPASSWD:
#       /bin/systemctl start your_service,
#       /bin/systemctl stop your_service
sudo systemctl stop calendar.service

calendar_home_path="./calendarhome.jar"

if [ -e "$calendar_home_path" ]; then
  echo "Calendar jar exist, deleting";
  rm "$calendar_home_path"
else
  echo "no file exists"
fi

files=$(find ./ -type f -name "*.jar")
echo "$files"
num_files=$(echo "$files" | wc -l)
echo "$num_files"

if [ "$num_files" -gt 1 ]; then
 echo "too many jars" >&2
 exit 1
fi

cp $(echo "$files" | head -n 1) "$calendar_home_path"

sudo systemctl start calendar.service