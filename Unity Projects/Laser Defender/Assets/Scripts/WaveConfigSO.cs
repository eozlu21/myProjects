using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(menuName = "Wave Config", fileName = "New Wave Config")]
public class WaveConfigSO : ScriptableObject
{
    [SerializeField] private Transform pathPrefab;
    [SerializeField] private List<GameObject> enemyPrefabs;
    [SerializeField] private float moveSpeed = 5f;
    [SerializeField] private float spawnTimeAverage = 1f;
    [SerializeField] private float spawnTimeVariance = 0f;
    [SerializeField] private float minSpawnTime = 0.2f;
    public Transform GetStartingWaypoint()
    {
        return pathPrefab.GetChild(0);
    }

    public int GetEnemyCount()
    {
        return enemyPrefabs.Count;
    }

    public GameObject GetEnemyPrefab(int index)
    {
        return enemyPrefabs[index];
    }
    public List<Transform> GetWaypoints()
    {
        List<Transform> waypoints = new List<Transform>();
        foreach (Transform waypoint in pathPrefab)
        {
            waypoints.Add(waypoint);
        }

        return waypoints;
    }

    public float GetMoveSpeed()
    {
        return moveSpeed;
    }

    public float GetRandomSpawnTime()
    {
        float spawnTime = Random.Range(spawnTimeAverage - spawnTimeVariance, spawnTimeAverage + spawnTimeVariance);
        return Mathf.Clamp(spawnTime, minSpawnTime, float.MaxValue);
    }
}