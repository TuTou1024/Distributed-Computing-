from pyspark import SparkConf,SparkContext

conf = SparkConf().setMaster("local").setAppName("grade1")
sc = SparkContext(conf=conf)
inputData = sc.textFile('/input/grades.txt')
# 过滤使得只留下必修
BXData = inputData.filter(lambda x: "必修" in x)
# 选出（姓名，分数）
mapData = BXData.map(lambda x: (x.split(",")[1],(int(x.split(",")[4]),1)))
# 求成平均分数
SumData = mapData.reduceByKey(lambda a, b: (a[0]+b[0], a[1]+b[1]))
ScoreData = SumData.map(lambda x: (x[0], x[1][0]/x[1][1]))
cnt1 = ScoreData.filter(lambda x: (x[1] >= 90) & (x[1] <= 100)).count()
cnt2 = ScoreData.filter(lambda x: (x[1] >= 80) & (x[1] < 90)).count()
cnt3 = ScoreData.filter(lambda x: (x[1] >= 70) & (x[1] < 80)).count()
cnt4 = ScoreData.filter(lambda x: (x[1] >= 60) & (x[1] < 70)).count()
cnt5 = ScoreData.filter(lambda x: (x[1] < 60)).count()
count = ["90-100:"+str(cnt1), "80-89:"+str(cnt2), "70-79:"+str(cnt3), "60-69:"+str(cnt4), "<60:"+str(cnt5)]
sc.parallelize(count).saveAsTextFile('/output4/spark_grade')


