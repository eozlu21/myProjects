using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Random = UnityEngine.Random;


public class Shooter : MonoBehaviour
{
    [Header("General")] [SerializeField] private GameObject projectilePrefab;
    [SerializeField] private float projectileSpeed = 10f;
    [SerializeField] private float projectileLifespan = 5f;
    [SerializeField] private float fireRatePerSecond = 2f;
    [Header("AI")] [SerializeField] bool useAI;
    [SerializeField] private float AiFireRateVar = 0.5f;
    [SerializeField] private float AiFireRateMean = 1.5f;
    [HideInInspector] public bool isFiring;
    private Coroutine firingCoroutine;


    private void Start()
    {
        if (useAI)
        {
            isFiring = true;
        }
    }

    private void Update()
    {
        Fire();
    }

    void Fire()
    {
        if (isFiring && firingCoroutine == null)
        {
            firingCoroutine = StartCoroutine(FireContinuously());
            
        }
        else if (!isFiring && firingCoroutine != null)
        {
            StopCoroutine(firingCoroutine);
            firingCoroutine = null;
        }
    }

    private IEnumerator FireContinuously()
    {
        while (true)
        {
            GameObject projectile = Instantiate(projectilePrefab, transform.position, Quaternion.identity);
            Rigidbody2D rb = projectile.GetComponent<Rigidbody2D>();
            rb.velocity = transform.up * projectileSpeed;
            Destroy(projectile, projectileLifespan);
            float waitTime;
            if (!useAI)
            {
                waitTime = 1f / fireRatePerSecond;
            }
            else
            {
                waitTime = Random.Range(1 / (AiFireRateMean - AiFireRateVar), 1 / (AiFireRateMean + AiFireRateMean));
            }
            Debug.Log(waitTime);
            yield return new WaitForSeconds(waitTime);
        }
    }
}