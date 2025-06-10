read -p "Enter commit message : " msg

git add .
git commit -m "$msg"

read -p "Enter your branch :" yb
git push -u origin "$yb"

echo "✅ Code pushed to Git successfully!"