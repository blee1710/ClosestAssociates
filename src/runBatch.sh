if [ $# -eq 0 ]
then
    echo "Usage: runBatch.sh <impl> <graph> <format> <op count>"
else
    ./opGen.py $2 "$3" $4 > test.in
    ./time.sh $1 test.in test.out $2
    cat test.out
fi
