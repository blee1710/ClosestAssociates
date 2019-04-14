: > $2 ## Clear output file

while read -r LINE; do
    echo "$LINE" | java -cp .:jopt-simple-5.0.2.jar GraphEvalTimed incmat -f assocGraph.csv vert.out edge.out neigh.out misc.out >> $2
done < $1
