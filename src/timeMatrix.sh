if [ $# -eq 0 ]
then
    echo "Usage: timeMatrix.sh <impl> <graphs> <formats> <batch size> <output>"
else
    : > $5

    while read -r FORMAT; do
        echo "Timing format $FORMAT"

        while read -r GRAPH; do
            echo "Timing graph $GRAPH"
            ./runBatch.sh $1 $GRAPH "$FORMAT" $4 >> $5
            echo >> $5
        done < $2
    done < $3
fi
