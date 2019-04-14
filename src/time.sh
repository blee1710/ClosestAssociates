: > $3 ## Clear output file

while read -r LINE; do
    echo "$LINE" | java -cp .:jopt-simple-5.0.2.jar GraphEvalTimed $1 -f $4 vert.out edge.out neigh.out misc.out >> $3
done < $2
