#!/bin/bash

# Check if branch name is provided as argument
if [ $# -eq 0 ]; then
    echo "Error: Please provide a branch name"
    echo "Usage: ./push.sh <branch-name> [commit message]"
    exit 1
fi

# Get branch name from first argument
BRANCH_NAME=$1

# Get commit message from second argument or use default
COMMIT_MSG=${2:-"Update changes"}

# Add all changes
git add .

# Commit changes with the message
git commit -m "$COMMIT_MSG"

# Push to the specified branch
git push origin "$BRANCH_NAME"

# Print success message
echo "Successfully pushed to branch: $BRANCH_NAME"