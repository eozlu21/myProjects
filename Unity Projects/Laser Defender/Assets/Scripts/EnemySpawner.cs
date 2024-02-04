using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawner : MonoBehaviour
{
    [SerializeField] private List<WaveConfigSO> waveConfigs;
    [SerializeField] private float timeBetweenWaves = 0.5f;
    [SerializeField] private bool isLooping;
    private WaveConfigSO currentWave;

    void Start()
    {
        StartCoroutine(CallWaves());
    }

    public WaveConfigSO GetCurrentWave()
    {
        return currentWave;
    }

    private IEnumerator CallWaves()
    {
        do
        {
            foreach (WaveConfigSO wave in waveConfigs)
            {
                yield return StartCoroutine(SpawnEnemiesInWave(wave));
                yield return new WaitForSeconds(timeBetweenWaves);
            }
        } while (isLooping);
    }

    private IEnumerator SpawnEnemiesInWave(WaveConfigSO wave)
    {
        currentWave = wave;
        for (int i = 0; i < wave.GetEnemyCount(); i++)
        {
            Instantiate(wave.GetEnemyPrefab(i),
                wave.GetStartingWaypoint().position,
                Quaternion.Euler(0,0,180), transform);
            yield return new WaitForSeconds(currentWave.GetRandomSpawnTime());
        }
    }
}